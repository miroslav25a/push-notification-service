# push-notification-service
A microservice for pushing notifications to Pushbullet devices.

## Motivation 
BBC technical test

## Installation 

### Pre-request
* Maven installation

### Build setup
Checkout the project from Github. Open a command window, as administrator, navigate to the root directory of the project, and run
> mvn clean install

## Test
>mvn test

## Usage
The application is a SpringBoot app, so there is no need to use a separate Tomcat to run it.

The simplest way to run the application is

>mvn spring-boot:run

In the web browser type in the following URL; http://localhost:8080/swagger-ui.html

### Create user example JSON
```
{
  "accessToken": "pushbullet api token",
  "username": "user1"
}
```

### Note notification example JSON
```
{
  "body": "hello world!",
  "title": "Test Title",
  "type": "note",
  "username": "user1"
}
```

### Link notification example JSON
```
{
  "body": "hello from Google",
  "title": "Google Link",
  "type": "link",
  "url": "http://www.google.com",
  "username": "user1"
}
```

### File notification example JSON
```
{
  "body": "test image",
  "type": "file",
  "file_name": "image.jpg",
  "file_type": "image/jpeg",
  "file_url": "http://dl.test.com/image.jpg", 
  "username": "user1"
}
```
