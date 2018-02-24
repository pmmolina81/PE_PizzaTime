trigger fOfferEventPublish on Fridays_offer__c (after insert, after update) {
	for(Fridays_offer__c fOffer:trigger.new)
    {
        if(system.today() == fOffer.date__c && 20 <= fOffer.Pizzas_ordered__c)
        {
            pizza_mountain__e pMountain = new pizza_mountain__e();
            pMountain.Pizza_lovers__c = fOffer.Pizza_lovers__c;
            pMountain.Pizzas_ordered__c = fOffer.Pizzas_ordered__c;
            
            // Call method to publish events
        	List<Database.SaveResult> results = EventBus.publish(new List<pizza_mountain__e>{pMountain});

        	// Inspect publishing result for each event
			for (Database.SaveResult sr : results) {
        		if (sr.isSuccess()) {
		        	System.debug('Successfully published event.');
	        	} else {
			        for(Database.Error err : sr.getErrors()) {
        				System.debug('Error returned: ' +
				        err.getStatusCode() +
			    	    err.getMessage());
        			}
        		}
        	}     
        }
    }
}