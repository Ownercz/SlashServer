SlashServer
===========
![Velocity API 3.5.0](https://img.shields.io/badge/Velocity-3.5.0-green.svg)
![Java 21](https://img.shields.io/badge/Java-21-blue.svg)

This plugin adds /server command aliases for velocity, for example  
/lobby is aliased to /server lobby  
/survival is aliased to /server survival  
Server commands require the permission nodes of server.\<name\>

Note: Server commands and permission nodes are the lowercase form of the server name

/ssreload reloads the plugin and required the permission node slashserver.reload


## CI
This project keeps both GitHub Actions and Gitea Actions builds.

- GitHub Actions keeps the built jar as a workflow artifact.
- Gitea Actions keeps the built jar as an artifact and can also upload it to an external destination when the required secrets are configured.
