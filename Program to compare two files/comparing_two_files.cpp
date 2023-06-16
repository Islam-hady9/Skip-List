#include <iostream>
#include <string>
#include <fstream>
using namespace std;

void print_differenct_lines(string, string, int);

int main()
{
	// Two strings for reading from files.
	string string_one;	// To read fron the first file.
	string string_two;	// To read fron the first second.

	// Two objects for the files
	ifstream file_read_one = ifstream("P1test2Output.txt"); // Original file.
	ifstream file_read_two = ifstream("output.txt");		// Testing file.

	// If the two files are opend.
	if (file_read_one.is_open() && file_read_two.is_open())
	{
		int count_lines = 0;
		// While the two files are good.
		while (file_read_one.good() && file_read_two.good())
		{
			// Get the line from the files.
			getline(file_read_one, string_one);
			getline(file_read_two, string_two);
			count_lines++;

			// Printing the two strings if they are not equals.
			if (string_one != string_two)
			{
				// dump function.
				string substring = "Node has depth ";
				if (string_one.find(substring) == 0 && string_two.find(substring) == 0)
				{
					// Node has depth 12, Value (r1, 10, 10, 5, 5)
					string st_1 = string_one.substr(0, 15);					// substring of the original file left.
					string st_2 = string_one.substr(string_one.find(", "));	// substring of the original file right.

					string st_3 = string_two.substr(0, 15);					// substring of the testing file left.
					string st_4 = string_two.substr(string_two.find(", "));	// substring of the testing file right.

					if (st_1 == st_3 && st_2 == st_4)
					{
						continue;
					}
					else
					{
						print_differenct_lines(string_one, string_two, count_lines);
					}
				}
				else if (string_one.find("Rectangles intersecting region") == 0 && string_two.find("Rectangles intersecting region") == 0)
				{
					string_one = string_one.substr(0, string_one.find(":"));
					string_two = string_two.substr(0, string_two.find(":"));

					if (string_one == string_two)
					{
						continue;
					}
					else
					{
						print_differenct_lines(string_one, string_two, count_lines);
					}
				}
				else
				{
					print_differenct_lines(string_one, string_two, count_lines);
				}
			}
		}
	}

	// closing the two files.
	file_read_one.close();
	file_read_two.close();
	system("pause");
	return 0;
}
void print_differenct_lines(string string_one, string string_two, int count_lines)
{
	cout << "line: " << count_lines << ": " << string_one << endl;
	cout << "line: " << count_lines << ": " << string_two << endl;
	cout << endl;
}