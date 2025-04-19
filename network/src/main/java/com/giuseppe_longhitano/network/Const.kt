package com.giuseppe_longhitano.network


const val API_VERSION = "api/v3"
const val API_KEY_QUERY = "x_cg_demo_api_key"


//Api Endpoints
const val MARKET = "${API_VERSION}/coins/markets"
const val COIN_DETAILS = "${API_VERSION}/coins/{id}"
const val CHARTS = "${API_VERSION}/coins/{id}/market_chart"

//Query params
const val QUERY_PARAM_VS_CURRENCY = "vs_currency"
const val QUERY_PARAM_PER_PAGE = "per_page"
const val QUERY_PARAM_PAGE = "page"
const val QUERY_PARAM_ID = "id"
const val QUERY_PARAM_DAYS = "days"
