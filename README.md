# Payara and MySQL Jakarta EE demo

A Jakarta EE 11 book catalog (Jakarta Faces page and REST API) running on Payara 7, with MySQL 8.4 in a second container. Docker Compose starts both. NetBeans opens the Maven project directly. Visual Studio Code can attach to the same debug port.

## Prerequisites

- Docker Desktop
- JDK 21
- Maven 3.9 or newer (only for local builds and the dev redeploy path; `docker compose up --build` builds inside the image)
- Apache NetBeans with a recent release that supports Jakarta EE 11, or Visual Studio Code with the Extension Pack for Java

Demo-only credentials:

| What | Value |
| --- | --- |
| Payara admin | `admin` / `admin` |
| MySQL database | `demo` |
| MySQL user | `demo` / `demo` |
| MySQL root | `root` / `root` |

## Run

From this directory:

```bash
docker compose up --build
```

Then open:

- Application: http://localhost:8080/library/
- REST: http://localhost:8080/library/api/books
- Admin console: http://localhost:4848
- MySQL from the host: `localhost:3306`

Payara listens for a debugger on port **9009** (`PAYARA_ARGS=--debug`). Attach an IDE on the host to that port.

The three sample books are inserted once, when the `books` table is empty. Adding a book in the page or via `POST /library/api/books` writes a row through the Payara JDBC resource `jdbc/demo`.

## Redeploy without rebuilding the image

`mvn package` writes `deployments/demo.war`. Start Compose with the dev override so that file is mounted into Payara:

```bash
mvn -B package
docker compose -f docker-compose.yml -f compose.dev.yaml up --build
```

Package again after a code change. Payara redeploys when the WAR in the deployments directory changes. Run `mvn package` before this Compose command. If `deployments/demo.war` is missing, Docker creates a directory at that path and the mount will not be a WAR.

## NetBeans

1. File -> Open Project and select this directory. NetBeans treats it as a Maven web project. There is no `nbproject` folder.
2. Right-click the project and choose **Compose Up**. That runs `docker compose up --build`.
3. Debug -> Attach Debugger. Host `localhost`, port `9009`. Transport is socket / JPDA.

To redeploy without rebuilding the image, right-click **Package WAR** first so `deployments/demo.war` exists, then right-click **Compose Up Dev**. After that stack is running, **Package WAR** again writes a new WAR and Payara redeploys it. Right-click **Compose Down** to stop the stack.

Optional remote server registration (deploy and admin from the IDE):

1. Install the Payara Tools plugin.
2. Services -> Servers -> Add Server -> Payara Server -> Remote Domain.
3. Host `localhost`, DAS port `4848`, user `admin`, password `admin`.
4. Point the installation directory at a local Payara 7 of the same version as the image (`payara/server-full:7.2026.8`).

Hot deploy of an exploded application needs that local Payara install and a Docker volume whose host path is the exploded app (or its parent) and whose container path matches what you enter as the Docker Volume in the server properties. The default loop in this demo does not need a local Payara install: build the WAR, let Compose deploy it, and attach the debugger.

## Visual Studio Code

Open this folder. Accept the recommended extensions (Extension Pack for Java and Docker). The same `.vscode` files work in Cursor.

- Terminal -> Run Task -> **Compose up** starts the stack and keeps running. The task signals ready when the log says the application was successfully deployed.
- Terminal -> Run Task -> **Package WAR** runs `mvn package`.
- Terminal -> Run Task -> **Compose up dev** bind-mounts `deployments/demo.war`. Run **Package WAR** before the first **Compose up dev**. Run **Package WAR** again after a code change to redeploy.
- Terminal -> Run Task -> **Compose down** stops the stack.
- Run and Debug -> **Attach to Payara** waits until `localhost:9009` is open, then connects. Start **Compose up** (or **Compose up dev**) before attaching.

## Layout

- `docker-compose.yml` — MySQL and Payara
- `compose.dev.yaml` — bind-mounts `deployments/demo.war`
- `payara/Dockerfile` — multi-stage Maven build, MySQL Connector/J, and the WAR
- `payara/post-boot-commands.asadmin` — JDBC pool `DemoPool` and resource `jdbc/demo`
- `src/main/java` — JPA `Book`, CDI service, REST `/api/books`, Faces bean
