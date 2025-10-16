package co.dev.victorroe.model.supplier;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Supplier {

    private Long id;
    private String name;
    private String lastName;
    private String address;
    private String company;
}
