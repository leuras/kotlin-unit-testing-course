package br.com.leuras.core.business.pipeline

fun interface PipelineStep {
    fun execute(input: Any): Any?
}