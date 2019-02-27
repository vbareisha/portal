package com.bareisha.portal.service;

import org.springframework.boot.SpringBootConfiguration;

/**
 * Данная конфигурация нужна для того, чтобы тестовые классы не добрались до {@link com.bareisha.portal.PortalApplication}
 * и не начали загружать весь контекст приложения
 */
@SpringBootConfiguration
public class StopConfiguration {
}
