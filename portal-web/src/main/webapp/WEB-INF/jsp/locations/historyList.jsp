<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>

<div class="row-fluid">
	<form class="form-horizontal">
		<div class="box">
			<div class="box-content">
				<div class="control-group">
					<label for="location" class="control-label">位置:</label>
					<div class="controls">
						<input type="text" name="location" class="input-small required"
							readonly="readonly" value="${locations.location}"> &nbsp;
						<input type="text" name="description"
							value="${locations.description}">
					</div>
				</div>
			</div>
		</div>
	</form>
</div>