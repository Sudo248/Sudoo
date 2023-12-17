package com.sudoo.paymentservice.service;

import javax.servlet.http.HttpServletRequest;

public interface IpService {
    String getIpAddress(HttpServletRequest request);
}
