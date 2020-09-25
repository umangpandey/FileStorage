# FileStorageMicroService

##################
FileStore is a microservice to imports .tar file extension,pull and list all files present in store.

To Run Module in MiniKube:

Step 1: Goto directory FileStorage/K8S/base/ folder

Step 2: kubectl apply -f fileStore-app.yaml
        kubectl apply -f fileStore-service.yaml
        
step 3: open url in browser (Do minikube ip) <Minikube-iP -Address>:30080/swagger-ui.html#/
        
###################

used JIB plugin in maven pom.xml to build the docker image

Max size the microservice can upload is 100MB for a single file .tar extension
