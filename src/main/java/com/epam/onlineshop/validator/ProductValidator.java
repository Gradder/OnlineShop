package com.epam.onlineshop.validator;

import com.epam.onlineshop.entities.Product;
import com.epam.onlineshop.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ProductValidator implements Validator {

    private final ProductService productService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Product.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        if (object == null) {
            return;
        }

        Product product = (Product) object;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        if (product.getName().length() < 2 || product.getName().length() > 32) {
            errors.rejectValue("name", "Size.productForm.name");
        }
        if (productService.isProductExist(product.getName())) {
            errors.rejectValue("name", "Duplicate.productForm.name");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "NotEmpty");
    }
}
