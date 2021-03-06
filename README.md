# cthulhu-text

[![java-version](https://img.shields.io/badge/java-17-green.svg)](https://openjdk.java.net/projects/jdk/17/)
[![tests](https://github.com/mtkhawaja/cthulhu-text/actions/workflows/main.yaml/badge.svg)](https://github.com/mtkhawaja/cthulhu-text/actions/workflows/main.yaml)
[![docker-build](https://github.com/mtkhawaja/cthulhu-text/actions/workflows/docker-image.yml/badge.svg)](https://github.com/mtkhawaja/cthulhu-text/actions/workflows/docker-image.yml)
[![codecov](https://codecov.io/gh/mtkhawaja/cthulhu-text/branch/main/graph/badge.svg?token=4ZRH0VLKJU)](https://codecov.io/gh/mtkhawaja/cthulhu-text)

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)

- Cthulhu text generation API.

## Quick Start

- API documentation available at [cthulhutext.com/swagger-ui](http://www.cthulhutext.com/swagger-ui/)

### Docker

#### Bash

```bash
docker build ./ -t cthulhu-text/api -f ./Dockerfile && \
docker run -rm -it -p 80:8080 cthulhu-text/api 
```

#### Powershell

```bash
docker build ./ -t cthulhu-text/api -f ./Dockerfile; `
if ($?) {`
  docker run --rm -it -p 80:8080  cthulhu-text/api `
}
```

## License

[![Licence](https://img.shields.io/github/license/Ileriayo/markdown-badges?style=for-the-badge)](./LICENSE)