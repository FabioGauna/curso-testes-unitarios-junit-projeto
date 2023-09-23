package com.algaworks.junit.utilidade;

import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

class SimuladorEsperaTest {

	@Test
//	@Disabled("Não é mais necessário")
	@EnabledIfEnvironmentVariable(named = "ENV", matches = "PROD")
	public void deveEsperarENaoDarTimeout() {
//		Assumptions.assumeTrue("PROD".equals(System.getenv("ENV")), ()-> "Não deve executar em PROD");
		assertTimeoutPreemptively(Duration.ofSeconds(1), () -> SimuladorEspera.esperar(Duration.ofMillis(10)));
	}
		
}
