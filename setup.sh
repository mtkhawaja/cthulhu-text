#!/usr/bin/env bash

# See SpringBoot docs for more information:
# https://docs.spring.io/spring-boot/docs/current/reference/html/deployment.html#deployment.installing

SERVICE_USER="${1:-$(whoami)}"
APPLICATION_HOME="/opt/cthulhu-text/bin"
JAR_NAME="ctext.jar"
SERVICE_NAME="ctext.service"
SERVICE_REGISTRATION_PATH="/etc/systemd/system/${SERVICE_NAME}"
SOURCE_CODE_LOCATION="/tmp/ctext-source"
DOWNLOAD_LINK="https://github.com/mtkhawaja/cthulhu-text.git"
BUILD_LOG="$(date +%Y-%m-%d-%H%M%S)-ctext-build-log.txt"

function abortSetup() {
  echo "❌ Setup Aborted: $1" | tee -a "${BUILD_LOG}" && exit 1
}
function successMessage() {
  echo "✅ $1" | tee -a "${BUILD_LOG}"
}

function pendingMessage() {
  echo "⌛ $1" | tee -a "${BUILD_LOG}"
}

function cleanupPreviousExecutions() {
  sourceCodeLocation="$1"
  applicationHome=$2
  serviceName="$3"
  searchLocations=("$sourceCodeLocation" "$applicationHome")

  if systemctl is-active --quiet "${serviceName}"; then
    pendingMessage "${serviceName} is running. Attempting to stop it..."
    sudo systemctl stop "${serviceName}" || abortSetup "Unable to stop running service: ${serviceName}"
    successMessage "${serviceName} stopped. Proceeding"

  else
    successMessage "${serviceName} is not running. Proceeding"
  fi

  for installationLocation in "${searchLocations[@]}"; do
    if [ -d "${installationLocation}" ]; then
      pendingMessage "Cleaning up previous installation artifacts from (${installationLocation})"
      sudo rm -rf "${installationLocation}" || abortSetup "Unable to cleanup installation artifacts in: '${installationLocation}'"
      successMessage "Clean up completed"
    fi
  done
}

function downloadApplication() {
  sourceCodeLocation="$1"
  repositoryUrl="$2"
  pendingMessage "Creating temporary installation directories... (${sourceCodeLocation})"
  mkdir -p "${sourceCodeLocation}" || abortSetup "Unable to create temporary installation directory ${sourceCodeLocation}"
  pendingMessage "Cloning source code from git..."
  git clone "${repositoryUrl}" "${sourceCodeLocation}" --quiet || abortSetup "Unable to download ${repositoryUrl}"
  successMessage "Source code downloaded successfully."
}

function buildApplication() {
  sourceCodeLocation="$1"
  originalDirectory="$(pwd)"
  cd "$sourceCodeLocation" || abortSetup "Unable to access ${sourceCodeLocation}"
  pendingMessage "Building application"
  mvn clean package spring-boot:repackage -DskipTests -q >"${BUILD_LOG}" || abortSetup "Maven build failed"
  cd "${originalDirectory}" || abortSetup "Unable to access ${originalDirectory}"
  successMessage "Build completed successfully"
}

function installApplication() {
  sourceCodeLocation=$1
  applicationHome=$2
  jarName=$3
  applicationPath="${applicationHome}/${jarName}"
  sudo mkdir -p "${applicationHome}" || abortSetup "Unable to create installation directory: ${applicationHome}"
  sudo find "${sourceCodeLocation}/target/" -name "api-*.jar" -exec mv '{}' "${applicationPath}" \;
}

function fixPermissions() {
  applicationPath="$1"
  serviceUser="$2"
  chmod +rx "${applicationPath}" || abortSetup "Unable to fix permissions for ${applicationPath}"
  sudo chown "${serviceUser}:${serviceUser}" "${applicationPath}" || abortSetup "Unable to update ownership for ${applicationPath}"
}

function configureService() {
  serviceName="$1"
  sudo systemctl enable "${serviceName}"
  sudo systemctl start "${serviceName}"
  sudo systemctl status "${serviceName}"
}

cleanupPreviousExecutions "${SOURCE_CODE_LOCATION}" "${APPLICATION_HOME}" ${SERVICE_NAME}
downloadApplication "${SOURCE_CODE_LOCATION}" "${DOWNLOAD_LINK}"
buildApplication "${SOURCE_CODE_LOCATION}"
installApplication "${SOURCE_CODE_LOCATION}" "${APPLICATION_HOME}" "${JAR_NAME}"
fixPermissions "${APPLICATION_HOME}/${JAR_NAME}" "${SERVICE_USER}"
pendingMessage "Updating service configuration:"
# Create Service File
sudo tee "$SERVICE_REGISTRATION_PATH" <<EOF
  [Unit]
  Description=cthulhu-text
  After=syslog.target

  [Service]
  User=${SERVICE_USER}
  ExecStart=/opt/cthulhu-text/bin/${JAR_NAME}
  SuccessExitStatus=143

  [Install]
  WantedBy=multi-user.target
EOF
successMessage "Service configuration updated"
configureService "${SERVICE_NAME}"
