package com.fusi.tool.gtw

import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class TimerGlobalFilter(): GlobalFilter {

    var log = LoggerFactory.getLogger(TimerGlobalFilter::class.java)

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val requestId = exchange.request.id
        val requestPath = exchange.request.path.toString()
        val startTime = System.currentTimeMillis()
        return chain.filter(exchange)
                .doOnSuccess {
                    val timeElapse = System.currentTimeMillis() - startTime
                    log.info("${requestId} - ${requestPath} success in ${timeElapse}")
                }
                .doOnError {
                    val timeElapse = System.currentTimeMillis() - startTime
                    log.info("${requestId} - ${requestPath} error in ${timeElapse}")
                }
    }
}