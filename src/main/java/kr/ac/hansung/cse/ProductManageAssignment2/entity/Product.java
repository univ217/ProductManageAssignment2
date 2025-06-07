package kr.ac.hansung.cse.ProductManageAssignment2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "상품명을 입력해주세요.")
    @Size(min = 1, message = "상품명은 한글자 이상 입력해주세요.")
    private String name;

    @NotNull(message = "브랜드명을 입력해주세요.")
    @Size(min = 1, message = "브랜드명은 한글자 이상 입력해주세요.")
    private String brand;

    @NotNull(message = "제조국을 입력해주세요.")
    @Pattern(regexp = "^[가-힣a-zA-Z\\s]+$", message = "제조국은 문자만 입력 가능합니다.")
    private String madeIn;

    @NotNull(message = "가격을 입력해주세요.")
    @Min(value = 10, message = "가격은 10원 이상이어야 합니다.")
    private Long price;
}

