package com.project.BankApplication.service;

import java.util.regex.Pattern;
/**
 * Some common constants */
public class BankConstants {
    public static final String SUCCESS =
            "Completed successfully";
    public static final String NO_ACCOUNT_FOUND =
            "Unable to find an account matching this account number";
    public static final String INVALID_SEARCH_CRITERIA =
            "The provided account number did not match the expected format";
    public static final String INSUFFICIENT_ACCOUNT_BALANCE =
            "No sufficient balance";
    public static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern.compile("\\d+");
    public static final String INVALID_TRANSACTION =
            "Account information is invalid or transaction is not performed. Please try again.";
    public static final String CREATE_ACCOUNT_FAILED =
            "Failed creating new account";
}
