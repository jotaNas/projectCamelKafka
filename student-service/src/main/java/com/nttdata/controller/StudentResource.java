package com.nttdata.controller;

import com.nttdata.dto.StudentDto;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
    public List<StudentDto> listAll(@QueryParam("page") @DefaultValue("0") int page,
                                    @QueryParam("size") @DefaultValue("10") int size) {
        return service.listAll(page, size);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid StudentDto newStudent) {
        try {
            service.addStudent(newStudent);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            log.error("Error creating student: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred").build();
        }
    }

    @GET
    @Path("/nome/{nome}")
    public Response getByName(@PathParam("nome") String nome) {
        try {
            List<StudentDto> studentDto = service.findByName(nome);
            return Response.ok(studentDto).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/cpf/{cpf}")
    public Response getByCpf(@PathParam("cpf") String cpf) {
        try {
            StudentDto studentByCpf = service.findByCpf(cpf);;
            return Response.ok(studentByCpf).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") @NotNull Long id) {
        try {
            StudentDto studentById = service.findById(id);
            return Response.ok(studentById).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") @NotNull Long id, @Valid StudentDto updateStudent) {
        try {
            service.updateStudent(id, updateStudent);
            return Response.noContent().build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred").build();
        }
    }


    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") @NotNull Long id) {
        service.deleteStudent(id);
        return Response.noContent().build();
    }
}

