package com.rdaniel.energyplatform.services;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.rdaniel.energyplatform.dtos.HistoricalMeasurementDTO;

import java.util.List;
import java.util.UUID;

@com.googlecode.jsonrpc4j.JsonRpcService("/api/rpc")
public interface JsonRpcService {

    List<HistoricalMeasurementDTO> historicalEC(@JsonRpcParam("days") int daysNo, @JsonRpcParam("device_id") UUID deviceId);

    List<Double> baselineEC(@JsonRpcParam("device_id") UUID deviceId);

}
