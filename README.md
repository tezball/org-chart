# Org Chart

This basic app is used to create and query an org chart for a given company. It contains two endpoints

* POST `/employee/hierarchy` - Allows an authenticated user to post a JSON payload to create the org chart
* GET `/employee/<employeeName>/supervisors?depth=<number>` - Allows an authenticated user to find the manager(s) of a given employee.

**NOTE:** Use the POST endpoint in postman to seed the H2 DB.

## API Docs

You can view the API docs from the following when the app is running locally.

* [Swagger UI](http://localhost:8080/swagger-ui.html)
* [JOSN contract](http://localhost:8080/api-docs/)
* [YMAL contract](http://localhost:8080/api-docs.yaml)

The authentication details are:

* **Username:** username
* **Password:** password

## App Structure

The app uses clean architecture as its structure.

![image info](/docs/images/clean_arch.png)

The responsibilities of each package/folder is as follows

* `api` - The endpoints to interact with the app
* `app` - Contains `usecases`, `domain` and interfaces for the detail layers. It could hold any others that support the `usecases` and/or `domain`.
* `usecases` - **Pure application logic.** Each class acts as a service class for a given business use case.
* `domain` - **Pure business logic.** Can never be aware of anything outside this package
* `storage` - Contains the code needed to persist the entities to a DB
* `framework` - This contains all things Spring. In this case the security and DI (beans)
* `resources` - Any config files, could hold config files for different envs and links to a config service on higher
  envs.
* `docs` - Any supporting docs for the given app
* `postman-collection` - The exported collection from Postman with BVTs included. Please keep up to date :)
* `k8s` - The YMAL files to deploy the app to Kubernetes or minikube locally.

## Storage

The storage used for this app is H2. This technical detail can be replaced with any relational DB given it uses JPA

H2 storage details:

* [URL](http://localhost:8080/h2-console/)
* **Driver class:** `org.h2.Driver`
* **username:** `sa`
* **Password:** There is no password, leave it blank
* **JDBC Url:** `jdbc:h2:mem:testdb`

## How to build and run the app

There are two ways to run the app

1. Use your IDE to do it for you
2. Run a set of commands to run the app

The commands in the second option can be used to help with deploying the app.

### Commands to run/deploy app

This option can be used as a BVT (build verification test) before creating a PR to merge back to develop (assuming
GitFlow is used)

The tools used for this section are as follows:

* Maven
* JDK 11
* Docker
* Minikube
* Kubectl
* Postman

1. Import the postman collection provided in the `postman-collection` folder.
2. Start minikube with `minikube start`
3. Optional: link minikube to your docker `eval $(minikube -p minikube docker-env)` (Required if running everything locally and can't pull image from docker hub)
4. `cd` into the root of the project and run the build with maven. The command is `mvn clean install`
5. Within the root of the project create the required image using docker `docker build -t org-chart:0.0.1 .` **NOTE:** Remove the old one if you are reusing the tag `docker image rm org-chart:0.0.1`
6. Within the root of the project create the `deployment.yaml`
   with `kubectl create deployment orgchart --image org-chart:0.0.1 -o yaml --dry-run=client > k8s/deployment.yaml`
7. Open the file `k8s/deployment.ymal` and add to the `containers` section `imagePullPolicy: Never`. This is related to
   step 3 and restrictions with using minikube
8. Within the root of the project create the `service.ymal` with the
   command `kubectl create service clusterip org-chart --tcp 80:8080 -o yaml --dry-run=client > k8s/service.yaml`
9. Within the root of the project run the command `kubectl apply -f ./k8s`. This will create the new service and create
   1 pod.
10. You can view the progress with the command `kubectl get all`
11. When the service/pod is running, you can forward the port with the
    command `kubectl port-forward svc/org-chart 8080:80`
12. Open postman and select the `Runner` option on the top right. A new window will open. Within that new window select
    the collection from step 1 and then select `Run Basic Org-chart`

You should see all the BVT's run and go green :)
The unit tests have been run with the command from step 4.

