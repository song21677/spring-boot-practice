package com.example.practice;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, SecondValidation.class})
public interface ValidationSequence {
}
