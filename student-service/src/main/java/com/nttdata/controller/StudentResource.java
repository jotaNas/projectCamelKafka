package com.nttdata.controller;

import com.nttdata.dto.StudentDto;
import com.nttdata.dto.response.ErrorResponse;
import com.nttdata.exception.DuplicateCpfException;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.slf4j.Logger;
import com.nttdata.service.StudentService;

import java.util.List;

@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {

    @Inject
    private StudentService service;

    Logger log = org.slf4j.LoggerFactory.getLogger(StudentService.class);

    @GET
    @Operation(summary = "List all students", description = "Lists all students with pagination")
    @APIResponse(responseCode = "200", description = "Ok", content =
    @Content(mediaType = "application/json", schema =
    @Schema(implementation = StudentDto.class)))
    @APIResponse(responseCode = "500", description = "Internal server error", content =
    @Content(mediaType = "application/json", schema =
    @Schema(implementation = ErrorResponse.class)))
    public Response listAll(@QueryParam("page") @DefaultValue("0") int page,
                            @QueryParam("size") @DefaultValue("10") int size) {
        try {
            List<StudentDto> students = service.listAll(page, size);
            return Response.ok(students).build();
        } catch (Exception e) {
            log.error("Error listing students: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Internal server error", getCurrentTimestamp()))
                    .build();
        }
    }

    @POST
    @Operation(summary = "Create a new student", description = "Creates a new student and returns its ID")
    @APIResponse(responseCode = "201", description = "Student created")
    @APIResponse(responseCode = "400", description = "Invalid input")
    @APIResponse(responseCode = "500", description = "Internal server error", content =
    @Content(mediaType = "application/json", schema =
    @Schema(implementation = ErrorResponse.class)))
    public Response create(@Valid StudentDto newStudent) {
        try {
            service.addStudent(newStudent);
            return Response.status(Response.Status.CREATED).build();
        } catch (DuplicateCpfException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage(), getCurrentTimestamp()))
                    .build();
        } catch (Exception e) {
            log.error("Error creating student: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Internal server error", getCurrentTimestamp()))
                    .build();
        }
    }

    @GET
    @Path("/nome/{nome}")
    @Operation(summary = "Get student by name", description = "Finds student(s) by name")
    @APIResponse(responseCode = "200", description = "Ok", content =
    @Content(mediaType = "application/json", schema =
    @Schema(implementation = StudentDto.class)))
    @APIResponse(responseCode = "404", description = "Student not found")
    @APIResponse(responseCode = "500", description = "Internal server error", content =
    @Content(mediaType = "application/json", schema =
    @Schema(implementation = ErrorResponse.class)))
    public Response getByName(@PathParam("nome") String nome) {
        try {
            List<StudentDto> students = service.findByName(nome);
            return Response.ok(students).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(Response.Status.NOT_FOUND.getStatusCode(), e.getMessage(), getCurrentTimestamp()))
                    .build();
        } catch (Exception e) {
            log.error("Error finding student by name: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Internal server error", getCurrentTimestamp()))
                    .build();
        }
    }

    @GET
    @Path("/cpf/{cpf}")
    @Operation(summary = "Get student by CPF", description = "Finds a student by CPF")
    @APIResponse(responseCode = "200", description = "Ok", content =
    @Content(mediaType = "application/json", schema =
    @Schema(implementation = StudentDto.class)))
    @APIResponse(responseCode = "404", description = "Student not found")
    @APIResponse(responseCode = "500", description = "Internal server error", content =
    @Content(mediaType = "application/json", schema =
    @Schema(implementation = ErrorResponse.class)))
    public Response getByCpf(@PathParam("cpf") String cpf) {
        try {
            StudentDto studentByCpf = service.findByCpf(cpf);
            return Response.ok(studentByCpf).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(Response.Status.NOT_FOUND.getStatusCode(), e.getMessage(), getCurrentTimestamp()))
                    .build();
        } catch (Exception e) {
            log.error("Error finding student by CPF: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Internal server error", getCurrentTimestamp()))
                    .build();
        }
    }

    @GET
    @Operation(summary = "Gets student by ID", description = "Student must exist")
    @APIResponse(responseCode = "200", description = "Ok", content =
    @Content(mediaType = "application/json", schema =
    @Schema(implementation = StudentDto.class)))
    @APIResponse(responseCode = "400", description = "Invalid ID supplied")
    @APIResponse(responseCode = "404", description = "Student not found")
    @APIResponse(responseCode = "500", description = "Internal server error", content =
    @Content(mediaType = "application/json", schema =
    @Schema(implementation = ErrorResponse.class)))
    @Path("/{id}")
    public Response getById(@PathParam("id") @NotNull Long id) {
        try {
            StudentDto studentById = service.findById(id);
            return Response.ok(studentById).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(Response.Status.NOT_FOUND.getStatusCode(), e.getMessage(), getCurrentTimestamp()))
                    .build();
        } catch (Exception e) {
            log.error("Error finding student by ID: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Internal server error", getCurrentTimestamp()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update student by ID", description = "Updates a student's information by ID")
    @APIResponse(responseCode = "204", description = "Student updated")
    @APIResponse(responseCode = "400", description = "Invalid ID supplied")
    @APIResponse(responseCode = "404", description = "Student not found")
    @APIResponse(responseCode = "500", description = "Internal server error", content =
    @Content(mediaType = "application/json", schema =
    @Schema(implementation = ErrorResponse.class)))
    public Response update(@PathParam("id") @NotNull Long id, @Valid StudentDto updateStudent) {
        try {
            service.updateStudent(id, updateStudent);
            return Response.noContent().build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(Response.Status.NOT_FOUND.getStatusCode(), e.getMessage(), getCurrentTimestamp()))
                    .build();
        } catch (Exception e) {
            log.error("Error updating student: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Internal server error", getCurrentTimestamp()))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete student by ID", description = "Deletes a student by ID")
    @APIResponse(responseCode = "204", description = "Student deleted")
    @APIResponse(responseCode = "400", description = "Invalid ID supplied")
    @APIResponse(responseCode = "404", description = "Student not found")
    @APIResponse(responseCode = "500", description = "Internal server error", content =
    @Content(mediaType = "application/json", schema =
    @Schema(implementation = ErrorResponse.class)))
    public Response delete(@PathParam("id") @NotNull Long id) {
        try {
            service.deleteStudent(id);
            return Response.noContent().build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(Response.Status.NOT_FOUND.getStatusCode(), e.getMessage(), getCurrentTimestamp()))
                    .build();
        } catch (Exception e) {
            log.error("Error deleting student: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Internal server error", getCurrentTimestamp()))
                    .build();
        }
    }

    private String getCurrentTimestamp() {
        return java.time.LocalDateTime.now().toString();
    }
}
