package br.com.cattle_control.starter.view;

import org.omnifaces.util.Faces;

import java.io.IOException;
import java.util.*;

import javax.faces.context.FacesContext;

import com.github.adminfaces.template.exception.BusinessException;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.com.cattle_control.starter.model.People;
import br.com.cattle_control.starter.model.Role;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.service.PeopleService;
import br.com.cattle_control.starter.service.RoleService;

import static br.com.cattle_control.starter.util.Utils.addDetailMessage;
import static com.github.adminfaces.template.util.Assert.has;

/**
* @autor Henrique Marcuzzo, Matheus Batistela
*/

@Component
@RequestScope
@RequiredArgsConstructor
public class PeopleFormView {

    @Autowired
    private final PeopleService peopleService;

    @Autowired
    private final RoleService roleService;

    private String[] selectedRolesList;

    private People people = new People();

    FacesContext context = FacesContext.getCurrentInstance();
    Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
    String personId = paramMap.get("id");

    public void init() {

        if(Faces.isAjaxRequest()){
           return;
        }
        if (personId != null) {

            int id = Integer.parseInt(personId); 
            people = peopleService.readById(id);
            List<Role> personRoles = people.getRoles(); 

            String[] setedRoles = personRoles.stream().map(Role::getName).toArray(String[]::new);

            setSelectedRolesList(setedRoles);
        } 

    }
    

    // Delegação de Getters and Setters das propriedades de people que serão usadas na tela
    public Integer getId() {
        return people.getId();
    }

    public String getName() {
        return people.getName();
    }

    public List<Role> getRoles() {
        return people.getRoles();
    }

    public String getEmail() {
        return people.getEmail();
    }

    public Integer getType() {
        return people.getType();
    }

    public String getIdType() {
        return people.getIdType(); 
    }

    public String getPhone() {
        return people.getPhone();
    }

    public String getInfo() {
        return people.getInfo();
    }

    public Boolean getDeleted() {
        return people.getDeleted();
    }

    public void setId(Integer id){
        people.setId(id);
    }

    public void setName(String name){
        people.setName(name);
    }

    public void setRoles(List<Role> roles) {
        people.setRoles(roles);
    }

    public void setEmail(String email){
        people.setEmail(email);
    }

    public void setType(Integer type){
        people.setType(type);
    }


    public void setIdType(String idType){
        people.setIdType(idType);
    }

    public void setPhone(String phone){
        people.setPhone(phone);
    }
    
    public void setInfo(String info){
        people.setInfo(info);
    }

    public void setDeleted(Boolean deleted) {
        people.setDeleted(deleted);
    }

    public String[] getSelectedRolesList() {
        return selectedRolesList;
    }

    public void setSelectedRolesList(String[] selected) {
        this.selectedRolesList = selected;
    }

    public void setSelectedRoles(String[] selectedRoles) {

        List<Role> allRoles = new ArrayList<>();
        
        for (String role : selectedRoles) {
            allRoles.add(roleService.findByRoleName(role));
        }
        
        people.setRoles(allRoles);
    }
    
    public List<String> listAllRoleNames() {
        List<String> a = roleService.readAllRoleName();
        return a;
    }

    public void save() {
        setSelectedRoles(getSelectedRolesList());
        this.people = People.builder()
                            .id(getId())
                            .name(getName())
                            .roles(getRoles())
                            .email(getEmail())
                            .type(getType())
                            .idType(getIdType())
                            .phone(getPhone())
                            .info(getInfo())
                            .deleted(false)
        					.build();
                            
        String msg;
        try {
            if (getId() == null) {
                peopleService.create(this.people);
                msg = "A pessoa " + getName() + " foi criada com sucesso!";
            } else {
                peopleService.update(this.people);
                msg = "A pessoa " + getName() + " foi atualizada com sucesso!";
            }

        } catch (final EntityAlreadyExistsException e) {
            msg = "Uma pessoa com o CPF/CNPJ " + getIdType() + " já existe no Banco de Dados!";

        } catch (final AnyPersistenceException e) {
            msg = "Erro na gravação dos dados!";

        }

        addDetailMessage(msg);
    }

    public void clear() {
        people = new People();
    }

    public void delete() throws IOException{
        String msg;
        if (has(getId())) {
            this.people = peopleService.readById(getId());
            try {
                setDeleted(true);
                peopleService.update(people);
                
                msg = "A Pessoa " + getName() + " foi removida com sucesso";

            } catch (final AnyPersistenceException e) {
                msg = "Erro na gravação dos dados!";

            } catch(final EntityAlreadyExistsException e) {
                msg = "Erro inesperado!";
            }
            
            addDetailMessage(msg);
            
            Faces.getFlash().setKeepMessages(true);
            Faces.redirect("people-list.jsf");
        } else {
            throw new BusinessException("Não existe ninguém para ser deletado!");
        }
    }

    

    // Funções auxiliares
    public boolean isNew() {
        return people == null || getId() == null;
    }



    
}
