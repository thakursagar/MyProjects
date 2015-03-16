using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DeveloperTestRunner
{
    class QuestionTester
    {
        private int passedTests = 0;
        private int totalTestCount = 0;
        private List<int> failedTestNumbers = new List<int>();
        private Dictionary<int, string> exceptionMessages = new Dictionary<int, string>();

        public bool AreEqual(object a, object b)
        {
            ++totalTestCount;
            var testResult = Object.Equals(a, b);

            if (testResult)
            {
                ++passedTests;
            }
            else
            {
                failedTestNumbers.Add(totalTestCount);
            }

            return testResult;
        }

        public void ImplementationThrewException(Exception e)
        {
            if (e == null) throw new ArgumentNullException("e");
            ++totalTestCount;
            failedTestNumbers.Add(totalTestCount);
            exceptionMessages[totalTestCount] = e.Message;
        }

        public string GetTestResults()
        {
            var exceptionResults = new StringBuilder();
            if (exceptionMessages.Count() > 0)
            {
                exceptionResults.Append("\r\n  Exception messages:");
                foreach (var message in exceptionMessages)
                {
                    exceptionResults.AppendFormat("\r\n    Test {0}: {1}", message.Key, message.Value);
                }
            }

            var failedTestsString = "";
            if (failedTestNumbers.Count > 0)
            {
                failedTestsString = String.Format(" Test(s) {0} failed.", String.Join(", ", failedTestNumbers));
            }

            return String.Format("{0}/{1} tests passed.{2}{3}",
                passedTests, totalTestCount, failedTestsString, exceptionResults.ToString());
        }
    }
}
