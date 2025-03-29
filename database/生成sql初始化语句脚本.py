# 使用传统的字符串格式化方法来避免f-string中的错误

def generate_insert_statements(data, table_name, columns):
    statements = []
    for index, row in data.iterrows():
        # Replace single quote with two single quotes for SQL insertion and handle non-string values
        values = ["'{}'".format(v.replace("'", "''")) if isinstance(v, str) else str(v) for v in row]
        # Create the INSERT statement using traditional string formatting
        statement = "INSERT INTO {} ({}) VALUES ({});".format(
            table_name, ', '.join(columns), ', '.join(values))
        statements.append(statement)
    return "\n".join(statements)

# 重新生成每个表的插入脚本
insert_scripts = {}
for key, data in file_contents.items():
    if key != "relation":  # Skip markdown file
        table_name = key if key != "movies" else "movies"  # Assuming table name is the same as the file name
        insert_scripts[key] = generate_insert_statements(data, table_name, data.columns.tolist())

# 将生成的脚本保存到文件中
script_paths = {}
for key, script in insert_scripts.items():
    script_path = f'/mnt/data/{key}_insert_script.sql'
    with open(script_path, 'w') as file:
        file.write(script)
    script_paths[key] = script_path

script_paths

