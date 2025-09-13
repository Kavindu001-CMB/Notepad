Notepad Web App
This is a simple Flask-based web application that acts as a notepad, allowing users to create, view, edit, and delete text notes stored in an SQLite database. The app provides a basic web interface for note management.
Setup Instructions
Prerequisites

Python 3.8 or higher (download from python.org).
Git installed (download from git-scm.com).
A GitHub account for hosting the code.

Steps to Set Up and Run

Clone or Create a GitHub Repository:

Create a new repository on GitHub (go to github.com, click "New repository," name it, e.g., notepad-app, and create it).
If cloning, run:git clone https://github.com/yourusername/notepad-app.git
cd notepad-app


Copy the provided app.py and this README.md into the notepad-app folder.


Install Dependencies:

Install Flask using pip:pip install flask


SQLite is built into Python, so no additional installation is required for the database.


Create the Database:

The app automatically creates an SQLite database (notes.db) when you first run it. The init_db() function in app.py creates a notes table with columns: id (auto-incrementing ID), title (note title), content (note text), and created_at (timestamp).


Run the Application:

In the terminal, navigate to the project folder (cd notepad-app) and run:python app.py


Open a web browser and go to http://127.0.0.1:5000 to access the notepad app.
Use the interface to add, edit, or delete notes.


Push Code to GitHub:

Stage and commit the files:git add app.py README.md
git commit -m "Initial commit for notepad app"


Push to GitHub:git push -u origin main





Assumptions and Special Notes

Database: Uses SQLite, which creates a local notes.db file in the project directory. No external database server is needed.
Dependencies: Requires Flask. Install it as shown above. No other external libraries are needed.
Environment: Tested with Python 3.8+. Ensure Python and pip are correctly configured (python --version and pip --version).
GitHub Import: Assumes you have a GitHub account and Git installed. Alternatively, you can upload app.py and README.md directly via GitHub's web interface (New repository > "uploading an existing file").
Functionality: The app supports basic note-taking (create, read, update, delete). It lacks user authentication or advanced features for simplicity.
File Size: Notes are stored as text in SQLite, so file size limits (e.g., GitHub's 100MB limit) are unlikely to be an issue. For large databases, consider Git LFS (git lfs install, then git lfs track "*.db").
Security: The app is for local development (runs in debug mode). For production, configure a WSGI server and secure the app (e.g., disable debug mode).

For more on GitHub setup, see GitHub Docs.







