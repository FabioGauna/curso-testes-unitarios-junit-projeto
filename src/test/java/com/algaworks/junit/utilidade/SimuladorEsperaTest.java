package com.algaworks.junit.utilidade;

import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class SimuladorEsperaTest {

	@Test
	@Disabled("Não é mais necessário")
	public void deveEsperarENaoDarTimeout() {
		assertTimeoutPreemptively(Duration.ofSeconds(1), () -> SimuladorEspera.esperar(Duration.ofMillis(10)));
	}
		
}
