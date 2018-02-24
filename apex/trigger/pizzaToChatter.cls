trigger pizzaToChatter on pizza_mountain__e (after insert) {
	for(pizza_mountain__e pMountain:trigger.new)
    {
		FeedItem post = new FeedItem();
		post.ParentId = UserInfo.getUserId();
		post.Body = '#pizzaTime '+pMountain.pizza_lovers__c;
		insert post;
    }
}