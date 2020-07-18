# Authentication plugin

This is an Authentication plugin for Minecraft servers, which is using Mongo database.

Tested on: `Spigot-1.14.4`

This plugin based to this project. [SpigotPluginBase](https://github.com/Nozemi/SpigotPluginBase)

---

## Usage:
Copy `AuthenticationPlugin.jar` file to your plugins folder and start you server.

### Default config.yml

Default database server uri is: `mongodb://localhost:27017/authDb`. If you want to change it, you can do that in `plugins/AuthenticationPlugin/config.yml` file.

In this file you can change the default messages and password minimum / maximum lengths too.

These messages are hard coded too. So if you want to remove one, you can do that with simply replace it an empty string in config.yaml.

```
authentication:
  database:
    mongodb:
      uri: mongodb://localhost:27017/authDb
  messages:
    notRegisteredUser: "You are not registered."
    userAlreadyExists: "You are already registered."
    userAlreadyLoggedIn: "You are already logged in."
    wrongPassword: "Wrong password."
    successfulLogin: "Successfully logged in."
    loginUsage: "Usage: /login <password>"
    invalidPassword: "Invalid password."
    successfulPasswordReset: "Password change successful."
    passwordChangeUsage: "Usage: /changepassword <old_password> <new_password> <new_password_again>"
    passwordMismatch: "Password mismatch."
    successfulRegistration: "Successfully registered."
    registrationUsage: "Usage: /register <password> <password_again>"
    successfulLogout: "Successfully logged out."
    passwordIsTooLong: "Password must not contain more than 32 characters."
    passwordIsTooShort: "Password must contain at least 8 characters."
  password:
    length:
      min: 8
      max: 32
```