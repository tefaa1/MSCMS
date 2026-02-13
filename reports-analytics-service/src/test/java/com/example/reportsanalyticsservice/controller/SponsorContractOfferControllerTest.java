package com.example.reportsanalyticsservice.controller;

import com.example.reportsanalyticsservice.dto.request.SponsorContractOfferRequest;
import com.example.reportsanalyticsservice.dto.response.SponsorContractOfferResponse;
import com.example.reportsanalyticsservice.service.SponsorContractOfferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SponsorContractOfferControllerTest {

    @Mock
    private SponsorContractOfferService sponsorContractOfferService;

    @InjectMocks
    private SponsorContractOfferController sponsorContractOfferController;

    @Test
    void testCreateSponsorContractOffer() {
        // Given
        SponsorContractOfferRequest request = new SponsorContractOfferRequest(
                "sponsor-keycloak-id-1",
                "Nike",
                "Kit Sponsorship Deal",
                "Exclusive kit sponsorship for 3 years",
                5000000L,
                LocalDate.of(2025, 7, 1),
                LocalDate.of(2028, 6, 30),
                "KIT",
                "PENDING",
                "Standard terms and conditions",
                "Brand visibility, marketing rights",
                1L
        );

        SponsorContractOfferResponse response = new SponsorContractOfferResponse(
                1L,
                "sponsor-keycloak-id-1",
                "Nike",
                "Kit Sponsorship Deal",
                "Exclusive kit sponsorship for 3 years",
                5000000L,
                LocalDate.of(2025, 7, 1),
                LocalDate.of(2028, 6, 30),
                "KIT",
                "PENDING",
                "Standard terms and conditions",
                "Brand visibility, marketing rights",
                1L,
                LocalDateTime.now()
        );

        given(sponsorContractOfferService.create(request)).willReturn(response);

        // When
        ResponseEntity<SponsorContractOfferResponse> result = sponsorContractOfferController.create(request);

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(201);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().id()).isEqualTo(1L);
        assertThat(result.getBody().sponsorName()).isEqualTo("Nike");
        assertThat(result.getBody().contractValue()).isEqualTo(5000000L);
        verify(sponsorContractOfferService, times(1)).create(request);
    }

    @Test
    void testUpdateSponsorContractOffer() {
        // Given
        Long id = 1L;
        SponsorContractOfferRequest request = new SponsorContractOfferRequest(
                "sponsor-keycloak-id-1",
                "Nike",
                "Kit Sponsorship Deal",
                "Exclusive kit sponsorship for 3 years",
                6000000L,
                LocalDate.of(2025, 7, 1),
                LocalDate.of(2028, 6, 30),
                "KIT",
                "APPROVED",
                "Updated terms and conditions",
                "Enhanced brand visibility, marketing rights",
                1L
        );

        SponsorContractOfferResponse response = new SponsorContractOfferResponse(
                1L,
                "sponsor-keycloak-id-1",
                "Nike",
                "Kit Sponsorship Deal",
                "Exclusive kit sponsorship for 3 years",
                6000000L,
                LocalDate.of(2025, 7, 1),
                LocalDate.of(2028, 6, 30),
                "KIT",
                "APPROVED",
                "Updated terms and conditions",
                "Enhanced brand visibility, marketing rights",
                1L,
                LocalDateTime.now()
        );

        given(sponsorContractOfferService.update(id, request)).willReturn(response);

        // When
        ResponseEntity<SponsorContractOfferResponse> result = sponsorContractOfferController.update(id, request);

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().id()).isEqualTo(1L);
        assertThat(result.getBody().contractValue()).isEqualTo(6000000L);
        assertThat(result.getBody().status()).isEqualTo("APPROVED");
        verify(sponsorContractOfferService, times(1)).update(id, request);
    }

    @Test
    void testGetSponsorContractOfferById() {
        // Given
        Long id = 1L;
        SponsorContractOfferResponse response = new SponsorContractOfferResponse(
                1L,
                "sponsor-keycloak-id-1",
                "Nike",
                "Kit Sponsorship Deal",
                "Exclusive kit sponsorship for 3 years",
                5000000L,
                LocalDate.of(2025, 7, 1),
                LocalDate.of(2028, 6, 30),
                "KIT",
                "PENDING",
                "Standard terms and conditions",
                "Brand visibility, marketing rights",
                1L,
                LocalDateTime.now()
        );

        given(sponsorContractOfferService.getById(id)).willReturn(response);

        // When
        ResponseEntity<SponsorContractOfferResponse> result = sponsorContractOfferController.getById(id);

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().id()).isEqualTo(1L);
        verify(sponsorContractOfferService, times(1)).getById(id);
    }

    @Test
    void testGetAllSponsorContractOffers() {
        // Given
        SponsorContractOfferResponse response1 = new SponsorContractOfferResponse(
                1L,
                "sponsor-keycloak-id-1",
                "Nike",
                "Kit Sponsorship Deal",
                "Exclusive kit sponsorship for 3 years",
                5000000L,
                LocalDate.of(2025, 7, 1),
                LocalDate.of(2028, 6, 30),
                "KIT",
                "PENDING",
                "Standard terms and conditions",
                "Brand visibility, marketing rights",
                1L,
                LocalDateTime.now()
        );

        SponsorContractOfferResponse response2 = new SponsorContractOfferResponse(
                2L,
                "sponsor-keycloak-id-2",
                "Adidas",
                "Stadium Naming Rights",
                "Exclusive stadium naming rights for 5 years",
                10000000L,
                LocalDate.of(2025, 8, 1),
                LocalDate.of(2030, 7, 31),
                "NAMING_RIGHTS",
                "APPROVED",
                "Premium terms and conditions",
                "Full branding rights, exclusive events",
                1L,
                LocalDateTime.now()
        );

        List<SponsorContractOfferResponse> responses = Arrays.asList(response1, response2);
        given(sponsorContractOfferService.getAll()).willReturn(responses);

        // When
        ResponseEntity<List<SponsorContractOfferResponse>> result = sponsorContractOfferController.getAll();

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody()).hasSize(2);
        verify(sponsorContractOfferService, times(1)).getAll();
    }

    @Test
    void testDeleteSponsorContractOffer() {
        // Given
        Long id = 1L;
        doNothing().when(sponsorContractOfferService).delete(id);

        // When
        ResponseEntity<Void> result = sponsorContractOfferController.delete(id);

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(204);
        verify(sponsorContractOfferService, times(1)).delete(id);
    }
}
