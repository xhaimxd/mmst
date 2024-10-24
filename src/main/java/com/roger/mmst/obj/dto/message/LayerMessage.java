package com.roger.mmst.obj.dto.message;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LayerMessage implements WsMessage {
    private Type type;
    private List<String> message;

    public static LayerMessage of(String message) {
        return new LayerMessage(Type.LAYER, List.of(message));
    }
}
