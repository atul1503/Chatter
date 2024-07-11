# Chat Application Backend

This is an excellent project for both newbies and advanced developers on how to write a proper Spring Boot backend for a chat application.

# API Documentation

## GET /users/login

**Description:** To login to the application.

### Request

- **Path Parameters:**
  - `id` (string): The ID of the resource.
- **Headers:**
  - `username` (string): The registered username.
  - `password` (string): The registered password.

### Response

- **200 OK:** Authentication successful

## GET /users/logout

**Description:** To logout from the application.

### Response

```json
{
  "message": "Logout successful"
}

```

## `GET /messages/latest`

### Description
Get the latest 5 messages from a sender to a receiver.

### Request

#### Query Parameters:

- `senderid` (string): ID of the sender.
- `receiverid` (string): ID of the receiver.

### Response

```
[
  {
    "messageId": 20,
    "time": "2024-07-11T18:16:41.039+00:00",
    "receiver": {
      "username": "atul",
      "password": "$2a$10$hnpcrI/no7.CQQ/l7APvUeTRbQZP/ygHonjIUZbtaT/49/zUuptxK",
      "authorities": null,
      "accountNonExpired": true,
      "accountNonLocked": true,
      "credentialsNonExpired": true,
      "enabled": true
    },
    "sender": {
      "username": "tadasha",
      "password": "$2a$10$O7EnQviF.VOMWScDoaciTuTjWX6WpM4sI3.pCPSFMH6I7aRFrVb7i",
      "authorities": null,
      "accountNonExpired": true,
      "accountNonLocked": true,
      "credentialsNonExpired": true,
      "enabled": true
    },
    "msgcontent": {
      "text": "3293583308_7ace2535ed_b.jpg",
      "photourl": "20.jpg",
      "type": "jpg"
    }
  }
]
```

# `POST /users/register`

## Description
To register a new user.

## Request

### Body:
- `username` (string): Chosen user ID.
- `password` (string): Chosen password.

## Response

### 305: User already present.
```json
{
  "status": 305,
  "message": "User already present."
}
```

## `POST /messages/createSimple`

### Description
To create a new text message.

### Request

#### Parameters:
- `senderid` (string): ID of the sender.
- `receiverid` (string): ID of the receiver.
- `text` (string): The message.

### Response

```json
{
  "status": 200,
  "message": "Message saved in database."
}
```

## `POST /messages/create`

### Description
To create a new image message.

### Request

#### Body:
- `senderid` (string): ID of the sender.
- `recid` (string): ID of the receiver.
- `image` (file): The image to upload.

### Response

```json
{
  "status": 200,
  "message": "Message saved."
}
```

## `GET /messages/image`

### Description
To retrieve an image by its name.

### Request

#### Query Parameters:
- `image_name` (string): The image name along with the extension.

### Response

#### 200 OK:
Binary data of the image.
