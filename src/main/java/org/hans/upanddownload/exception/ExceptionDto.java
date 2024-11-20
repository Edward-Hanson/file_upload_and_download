package org.hans.upanddownload.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ExceptionDto {
    Integer exceptionCode;
    List<String> message;
    LocalDateTime timeStamp;
}
