package com.chatting.config;

import com.typesafe.config.Config;

import static com.typesafe.config.ConfigFactory.load;

public class ConfigProvider {

    static public final Config config = load().withFallback(load("application"));
}
