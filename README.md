This is an excellent project to newbie to advanced people on how to write proper spring boot backend for a chat application.

API Documentation:

- GET /users/login

    Description: To login to the application
    
    Request:
      Path Parameters:
           id: (string) The ID of the resource.
      Headers:
           username: (string) the registered username.
           password: (string) the registered password.

    Response:
      Authentication successful

- GET /users/logout

    Description: To logout from the application

    Response:
        {"message": "Logout successful"}

- GET /messages/latest?senderid=tadasha&receiverid=atul

    Description: get the latest 5 messages from a sender to receiver.

    Request:
      Parameters:
          senderid: (string) id of the sender.
          receiverid: (string) id of the reciever.

    Response:
       `[
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
       ]`
  
 - POST /users/register
       Description: to register any user.

       Request:
           Body:
               username: (string) chosen userid.
               password: (string) chosen password.
       Response:
           `{
                "status": 305,
                "message": "User already present."
            }`

- POST /messages/createSimple
          Description: to create a new message of text.
          Request:
              Params:
                  senderid: (string) id of the sender.
                  receiverid: (string) id of the reciever.
                  text: (string) the message.
          Response:
              `{
                    "status": 200,
                    "message": "Message saved in database."
               }`

- POST /messages/create
          Description: to create a new message as image.
          Request:
              Body:
                  senderid
                  recid
                  image: (file) the image to upload
          Response:
                `{
                    "status": 200,
                    "message": "Message saved."
                }`

- GET /messages/image
          Description: to get the iamge as file by image name.
          Request:
              image_name: (string) the image name along with the extension.
          Response:
              (Binary data of image).
   
           
