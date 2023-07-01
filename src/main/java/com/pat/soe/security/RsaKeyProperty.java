package com.pat.soe.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "rsa")
public record RsaKeyProperty(RSAPrivateKey rsaPrivateKey, RSAPublicKey rsaPublicKey) {
}
