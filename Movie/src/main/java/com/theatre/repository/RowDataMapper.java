package com.theatre.repository;

import java.sql.ResultSet;

public interface RowDataMapper<T> {

	public T mapRow(ResultSet rs, int rowNum);
}
