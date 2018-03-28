package com.ttn.linksharing
/*
Question 13 and Question 14 both are implemented on this branch
Question 13 It should create resource only if there is not data in resource table
Question 14 Description of the resource should include topic name
*/

class BootStrap {

    def init = { servletContext ->

        createUsers()
        createTopics()
        createResource()
          }
    void createUsers(){


        //admin
        if(User.count()==0) {
            User admin = new User(email: "admin@gmail.com", password: DefaultPassword.PASSWORD, firstName: "admin", lastName: "portal", userName: 'adminPortal', photo: 121, admin: true, active: true)
            if(admin.save()){
                log.info("Admin Saved Successfully")
            }
            else {
                log.error("error: ${admin.errors.getAllErrors()}")
            }

            //normal
            User normal = new User(email: "prachijulka@gmail.com", password: DefaultPassword.PASSWORD, firstName: "Prachi", lastName: "Julka", userName: 'PrachiJulka', photo: 122, admin: false, active: true)
            if(normal.save()){
                log.info("Normal User Saved Successfully")
            }
            else {
                log.error("error: ${normal.errors.getFieldErrors()}")
            }

        }
    }

    void createTopics() {
        List<User> userCount = User.getAll()
        userCount.each {

            if (Topic.findAllByCreatedBy(it).size()==0) {

                Topic topic = new Topic(name: "BigData", createdBy: it, visibility: Visibility.PUBLIC)

                Topic topic1 = new Topic(name: "Java", createdBy: it, visibility: Visibility.PRIVATE)

                Topic topic2 = new Topic(name: "nodeJs", createdBy: it, visibility: Visibility.PUBLIC)

                Topic topic3 = new Topic(name: "AngularJs", createdBy: it, visibility: Visibility.PRIVATE)

                Topic topic4 = new Topic(name: "MachineLearning", createdBy: it, visibility: Visibility.PUBLIC)


                if(topic.save())
                    it.addToTopics(topic)

                if(topic1.save())
                    it.addToTopics(topic1)

                if(topic2.save())
                    it.addToTopics(topic2)

                if(topic3.save())
                    it.addToTopics(topic3)

                if(topic4.save())
                    it.addToTopics(topic4)


                else {
                    log.error("Topic ${topic.errors.getFieldErrors()}")
                    log.error("Topic ${topic1.errors.getFieldErrors()}")
                    log.error("Topic ${topic2.errors.getFieldErrors()}")
                    log.error("Topic ${topic3.errors.getFieldErrors()}")
                    log.error("Topic ${topic4.errors.getFieldErrors()}")

                }
                it.save()
                log.info("Topics Saved Successfully")


            }
        }
    }
        void createResource(){

            if(Resource.count()==0) {

                List<Topic> topics = Topic.getAll()

                topics.each {
                    Resource resource = new LinkResource(url: "https://en.wikipedia.org/wiki/Big_data", description: "${it.name} url", topic: it, user: it.createdBy)
                    Resource resource1 = new LinkResource(url: "https://www.sas.com/en_in/insights/big-data/what-is-big-data.html", description: "${it.name} bigdata", topic: it, user: it.createdBy)
                    Resource resource2 = new DocumentResource(filePath: "fvnkdfvdk", description: "${it.name} cndfbcfefbfer", user: it.createdBy, topic: it)
                    Resource resource3 = new DocumentResource(filePath: "nvdjfn", user: it.createdBy, description: "${it.name} sdns", topic: it)

                    if (resource.save()) {
                        it.addToResources(resource)
                        it.createdBy.addToResources(resource)
                    }
                    else {
                        log.error("Resource Error: ${resource.errors.allErrors}")
                    }
                    if (resource1.save()) {
                        it.addToResources(resource1)
                        it.createdBy.addToResources(resource1)
                    }
                    else {
                        log.error("Resource Error: ${resource1.errors.allErrors}")
                    }
                    if(resource2.save()){
                        it.addToResources(resource2)
                        it.createdBy.addToResources(resource2)

                    }
                    else{
                        log.error("Resource Error: ${resource2.errors.allErrors}")

                    }
                    if(resource3.save()){
                        it.addToResources(resource3)
                        it.createdBy.addToResources(resource3)

                    }
                    else{
                        log.error("Resource Error: ${resource3.errors.allErrors}")
                      }
                    createReadingItemIfItDoesNotExistsInUsersReadingItem(it.createdBy,it)
                    it.createdBy.save()
                    it.save()
                }

            }


        }


    def destroy = {
    }
}
