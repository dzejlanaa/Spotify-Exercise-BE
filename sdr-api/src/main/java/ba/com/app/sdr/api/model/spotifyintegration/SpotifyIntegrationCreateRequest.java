package ba.com.app.sdr.api.model.spotifyintegration;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for creation of a spotify integration")
public class SpotifyIntegrationCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(description = "Name of the spotify integration")
    private String name;
    @NotBlank
    @Schema(description = "Request of the spotify integration")
    private String request;
    @NotBlank
    @Schema(description = "Response of the spotify integration")
    private String response;
    @NotBlank
    @Schema(description = "Type of the object which fetched data represents")
    private String objectType;
    @Min(0)
    @Schema(description = "Id of the object which fetched data represents")
    private Long objectId;
}