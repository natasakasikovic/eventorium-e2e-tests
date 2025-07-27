package com.ts.eventorium.event.util;

import java.time.LocalDate;

public record EventFilter(String name, String description, String type, String city, Integer maxParticipants, LocalDate from, LocalDate to) { }