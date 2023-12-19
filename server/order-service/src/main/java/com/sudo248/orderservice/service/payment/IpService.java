package com.sudo248.orderservice.service.payment;

import javax.servlet.http.HttpServletRequest;

public interface IpService {
    String getIpAddress(HttpServletRequest request);
}
