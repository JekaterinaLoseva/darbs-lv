package com.example.darbslv.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OriginalLinkResolverTest {

    @Test
    void testResolverReturnsSameUrlOnError() {
        OriginalLinkResolver resolver = new OriginalLinkResolver();

        String badUrl = "http://nonexistent.domain.fake";
        String result = resolver.resolve(badUrl);

        assertThat(result).isEqualTo(badUrl);
    }
}
