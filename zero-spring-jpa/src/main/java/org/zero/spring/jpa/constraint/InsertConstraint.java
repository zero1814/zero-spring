package org.zero.spring.jpa.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.zero.spring.jpa.annotaion.Insert;

public class InsertConstraint implements ConstraintValidator<Insert, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return false;
	}

}
