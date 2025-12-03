package com.example.darbslv.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VisidarbiParserTest {

    @Test
    void testParserInstantiates() {
        VisidarbiParser parser = new VisidarbiParser();
        assertThat(parser).isNotNull();
    }
}
