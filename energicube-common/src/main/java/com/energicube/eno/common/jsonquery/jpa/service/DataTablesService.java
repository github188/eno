package com.energicube.eno.common.jsonquery.jpa.service;

import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import java.io.Serializable;
import java.util.List;


@Service
public abstract class DataTablesService<T extends Serializable> implements
		IDataTablesService<T> {

	@Autowired
	private EntityManagerFactory emf;
	
	  /**
     * Getting the repository
     * @return
     */
    public abstract PagingAndSortingRepository<T, Long> getRepository();
	
	@Transactional(readOnly = true)
	public DataTablesResponse<T> read(DataTablesRequestParams params) {
		

		DataTablesResponse<T> result = new DataTablesResponse<T>();
        Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC : Direction.DESC;
        String sortColName = params.getsColumns().split(",")[params.getiSortCol_0()];
        Sort sort = new Sort(sortDir, sortColName);
        int pageNumber = (int) Math.ceil(params.getiDisplayStart() / params.getiDisplayLength());
        Page<T> page = getRepository().findAll(new PageRequest(pageNumber, params.getiDisplayLength(), sort));
        List<T> data = page.getContent();
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(page.getTotalElements());
        result.setiTotalRecords(page.getTotalElements());
        result.setAaData(data);
        return result;
	}

}
