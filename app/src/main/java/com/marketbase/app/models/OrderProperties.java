package com.marketbase.app.models;

import java.util.Arrays;
import java.util.List;

public class OrderProperties {
	// status properties
	public String CREATED = "Created";
	public String READY_TO_DEPLOY = "Ready to deploy";
	public String DEPLOYING = "Deploying";
	public String DEPLOYED = "Deployed";
	public String COMPLETED = "Completed";
	public String FAILED = "Failed";

	public List<String> paymentGateways = Arrays.asList("Yandex Checkout");
}
