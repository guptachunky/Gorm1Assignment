package com.ttn.linksharing
/*Creator of topic should automatically be subscribed to topic (Use after insert event of topic)
*/

class Topic {

    String name
    Date dateCreated
    Date lastUpdated
    Visibility visibility
    static belongsTo = [ createdBy : User]
    static hasMany = [subscriptions:Subscription, resources:Resource]
    static constraints = {
        name(blank: false, nullable: false, unique: 'createdBy')
        visibility(nullable: false)
    }

    def afterInsert() {
        log.info "----------Into After Insert------"
        Topic.withNewSession {
            Subscription subscription= new Subscription(topics: this,seriousness: Seriousness.CASUAL,user: this.createdBy)
            subscription.save()
        }


    }



}




