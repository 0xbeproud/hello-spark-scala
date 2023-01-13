s3_data_lake = s3://krosslab-prod-lake
path = empty

download:
	#aws s3 cp --recursive --exclude "*" --include "*.parquet" $(s3_data_lake)/$(path)/ data-lake/$(path)
	aws s3 cp --recursive --exclude "*" --include "*.parquet" $(s3_data_lake)/$(path)/ data-lake/$(path)
