package com.mercedes.challenge.automationtest.page;

public class CarConfiguratorPage {
    public static final String CAR_CONFIGURATOR_SHADOWROOT = "body > div.root.responsivegrid > div > div > div > owcc-car-configurator";
    public static final String CAR_CONFIGURATOR_EXPAND_FUEL = "#cc-app-container-main > div.cc-app-container__main-frame.cc-grid-container > " +
            "div.cc-grid-container.ng-star-inserted > div > div.cc-app-container__content-selectables-container > cc-motorization > " +
            "cc-motorization-filters > cc-motorization-filters-form > form > div > div.cc-motorization-filters-form__primary > " +
            "div.cc-motorization-filters-form__primary-filters.ng-star-inserted > cc-motorization-filters-primary-filters > div > fieldset > wb-multi-select-control > button";
    public static final String FUEL_DIESEL_SELECTOR = "#cc-app-container-main > div.cc-app-container__main-frame > div > div > div.cc-app-container__content-selectables-container" +
            " > cc-motorization > cc-motorization-filters > cc-motorization-filters-form > form > div > div.cc-motorization-filters-form__primary > " +
            "div.cc-motorization-filters-form__primary-filters.ng-star-inserted > cc-motorization-filters-primary-filters > div > fieldset > wb-multi-select-control > " +
            "div > div > wb-checkbox-control:nth-child(1) > label > div > wb-icon";
    public static final String TAKE_SCREENSHOT = "#cc-app-container-main > div.cc-app-container__main-frame.cc-grid-container > div.cc-grid-container.ng-star-inserted > div > " +
            "div.cc-app-container__content-selectables-container > cc-motorization > cc-motorization-comparison";
    public static final String GET_RESULTS = "#cc-app-container-main > div.cc-app-container__main-frame.cc-grid-container > div.cc-grid-container.ng-star-inserted > div > " +
            "div.cc-app-container__content-selectables-container > cc-motorization > cc-motorization-comparison > div > div > div:nth-child(n)";
    public static final String AMOUNT_SELECTOR = "wb-card > div.cc-motorization-comparison-header-wrapper > cc-motorization-header > div > div > div:nth-child(3) > span";

}
