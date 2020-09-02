package com.fusi.tool.gtw

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@SpringBootApplication
class GtwApplication

fun main(args: Array<String>) {
    runApplication<GtwApplication>(*args)
}

interface GtwService {
    fun getConcurrentRequest(): Long
    fun incConcurrentRequest(): Long
    fun decConcurrentRequest(): Long
}

@Service
class GtwServiceImpl: GtwService {
    val atomicLong = AtomicLong()

    override fun getConcurrentRequest() = atomicLong.get()
    override fun incConcurrentRequest() = atomicLong.incrementAndGet()
    override fun decConcurrentRequest() = atomicLong.decrementAndGet()
}

@RestController
@RequestMapping("/gtw")
class GtwController(val gtwService: GtwService){

    @GetMapping("concurrentRequest")
    fun getConcurrentRequest() = mapOf("concurrentRequest" to gtwService.getConcurrentRequest())

}