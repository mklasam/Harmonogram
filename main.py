from flask import Flask,render_template, request
from flask_mysqldb import MySQL
from flask import jsonify
from flask import Response
import json
import requests


app = Flask(__name__)
 
app.config['MYSQL_HOST'] = 'localhost'
app.config['MYSQL_USER'] = 'root'
app.config['MYSQL_PASSWORD'] = ''
app.config['MYSQL_DB'] = 'myflaskapp_'
 
mysql = MySQL(app)
 
@app.route('/form')
def form():
    return render_template('form.html')
 
@app.route('/login', methods = ['POST'])
def login():
    print("RES")
    retmsg = "OK"
    if(request.method == 'POST'):
        username = request.form['username']
        password = request.form['password']
        cur = mysql.connect.cursor()
        cur.execute("SELECT * FROM users WHERE username = %s AND password = %s", (username, password))
        user = cur.fetchone()
        ret2 = jsonify(user)
        print(ret2)
        return ret2
    msg = "Error"
    ret = jsonify(msg=retmsg)
    return [ret, 400]
    


@app.route('/register', methods=['POST', 'GET'])
def register():
    if request.method == 'POST':
        name = request.form['name']
        email = request.form['email']
        username = request.form['username']
        password = request.form['password']
        cur = mysql.connect.cursor()
        cur.execute("INSERT INTO users(name, email, username, password) VALUES(%s, %s, %s, %s)",
                    (name, email, username, password))
        cur.connection.commit()
        cur.close()
        ret = jsonify(
            name=request.form['name'],
            email=request.form['email'],
            username=request.form['username'],
            password=request.form['password']
            )
        print(ret)
        return ret, 200
    return 400

@app.route('/crud_panel')
def crud_panel():
    cur = mysql.connect.cursor()
    cur.execute("SELECT * FROM  schedule")
    row_headers = [x[0] for x in cur.description]
    rv = cur.fetchall()
    json_data = []
    msg="PK"
    for result in rv:
        json_data.append(dict(zip(row_headers, result)))
    my_dict = {msg:json_data}
    response = jsonify(my_dict)
    return response


@app.route('/add_task', methods=['POST'])
def add_record():
    cur = mysql.connect.cursor()
    if request.method == 'POST':
        start_day = request.form['start_day']
        finish_day = request.form['finish_day']
        hour = request.form['hour']
        author = request.form['author']
        taskName = request.form['taskName']
        descriptionTask = request.form['descriptionTask']
        cur.execute("INSERT INTO schedule (start_day, finish_day, hour, author, taskName, descriptionTask) VALUES (%s,%s,%s,%s,%s,%s)", (start_day, finish_day, hour, author, taskName, descriptionTask))
        cur.connection.commit()
        status="Record added successfully"
        ret = jsonify(
            status=status
        )
        return ret

@app.route('/edit/<id>', methods=['POST', 'GET'])
def get_record(id):
    cur = mysql.connect.cursor()
    cur.execute('SELECT * FROM schedule WHERE id = %s', [id])
    row_headers = [x[0] for x in cur.description]
    rv = cur.fetchall()
    json_data = []
    for result in rv:
        json_data.append(dict(zip(row_headers, result)))
    ret = jsonify(json_data)
    return ret


@app.route('/update/<id>', methods=['POST'])
def update_record(id):
    cur = mysql.connect.cursor()
    if request.method == 'POST':
        start_day = request.form['start_day']
        finish_day = request.form['finish_day']
        hour = request.form['hour']
        author = request.form['author']
        taskName = request.form['taskName']
        descriptionTask = request.form['descriptionTask']
        cur.execute("""
        UPDATE schedule
        SET start_day = %s,
        finish_day = %s,
        hour = %s,
        author = %s,
        taskName = %s,
        descriptionTask = %s
        WHERE id = %s
        """, (start_day, finish_day,hour, author, taskName, descriptionTask, id))
        cur.connection.commit()
        msg = "Updated!"
        ret = jsonify(msg=msg)
        return ret

@app.route('/delete/<string:id>', methods=['POST', 'GET'])
def delete_record(id):
    cur = mysql.connect.cursor()
    cur.execute('DELETE FROM schedule WHERE id = {0}'.format(id))
    cur.connection.commit()
    msg = 'Record Removed Successfully'
    ret = jsonify(msg=msg)
    return ret


app.run(host='localhost', port=5000)