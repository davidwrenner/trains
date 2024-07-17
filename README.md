# Trains
[![Build](https://github.com/davidwrenner/trains/actions/workflows/build.yml/badge.svg?branch=main)](https://github.com/davidwrenner/trains/actions/workflows/build.yml)

A live map of metro trains in DC.


## Local setup
### Prerequisites
- Java 22
- API key from [WMATA](https://developer.wmata.com):
```
export trains_api_key=<API KEY>
```

### Build
```
mvn clean package
```

### Run

```
java -jar target/trains.jar
```

## Demo
https://github.com/user-attachments/assets/71b66670-7895-4588-8690-47ee79fc6e20

*demo sped up 16x
