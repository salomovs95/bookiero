package com.salomovs.bookiero.annotation;

import java.util.Map;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

//import com.salomovs.bookiero.view.dto.HttpResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary="")
@SecurityRequirement(name="")
@ApiResponses({
  @ApiResponse(
    responseCode="200",
    content=@Content(
      mediaType="application/json",
      schema=@Schema(
        implementation=Object.class,
        example="{}"
      )
    )
  ),
  @ApiResponse(
    responseCode="201",
    content=@Content(
      mediaType="application/json",
      schema=@Schema(
        implementation=Map.class,
        example="{ \"id\": 1 }"
      )
    )
  ),
  @ApiResponse(
    responseCode="400",
    content=@Content(
      mediaType="application/json",
      schema=@Schema(
        implementation=Map.class,
        example="{ \"error\": \"something went wrong\" }"
      )
    )
  ),
  @ApiResponse(
    responseCode="401",
    content=@Content(
      mediaType="application/json",
      schema=@Schema(
        implementation=Map.class,
        example="{ \"error\": \"unauthorized\" }"
      )
    )
  ),
  @ApiResponse(
    responseCode="404",
    content=@Content(
      mediaType="application/json",
      schema=@Schema(
        implementation=Map.class,
        example="{ \"error\": \"data requested not found\" }"
      )
    )
  ),
  @ApiResponse(
    responseCode="500",
    content=@Content(
      mediaType="application/json",
      schema=@Schema(
        implementation=Map.class,
        example="{ \"error\": \"something went wrong\" }"
      )
    )
  )
})
public @interface ApiOperation {
  @AliasFor(annotation=Operation.class, attribute="summary")
  String summary() default "";

  @AliasFor(annotation=SecurityRequirement.class, attribute="name")
  String security() default "";
}
