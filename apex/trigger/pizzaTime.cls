trigger pizzaTime on TwitterPizza__e (after insert) 
{
    List<Pizza_Order__c> pizzas = new List<Pizza_Order__c>();

    List<Fridays_offer__c> fridayOffer = [select id, date__c, Pizza_lovers__c from Fridays_offer__c where date__c = today];
    Fridays_offer__c newOrder = new Fridays_offer__c();
    
    if(0 == fridayOffer.size())
    {
        newOrder.date__c = system.today();
        fridayOffer.add(newOrder);
        insert fridayOffer;
    }
    
    for(TwitterPizza__e tPizza:trigger.new)
    {        
        Pizza_Order__c pizza = new Pizza_Order__c();
        Fridays_offer__c newLover = new Fridays_offer__c();
        
        pizza.tweet__c = tPizza.tweet__c;
        pizza.User__c = tPizza.User_Name__c;
        pizza.Date__c = system.today();
        pizza.Fridays_offer__c = fridayOffer.get(0).id;
        pizzas.add(pizza);
        
        if(null == fridayOffer.get(0).Pizza_lovers__c){
            fridayOffer.get(0).Pizza_lovers__c = tPizza.User_Name__c;
        } else
        {
            fridayOffer.get(0).Pizza_lovers__c = fridayOffer.get(0).Pizza_lovers__c + ';' + tPizza.User_Name__c;
        }    
    }
    
    if(0 <> pizzas.size())
    {
        insert pizzas;
        update fridayOffer;
    }
}